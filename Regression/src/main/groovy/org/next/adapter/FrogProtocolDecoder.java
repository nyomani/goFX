package org.next.adapter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.ByteBuffer;

public class FrogProtocolDecoder extends CumulativeProtocolDecoder {
    int messageLen = -1;
    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        boolean returnValue = false;
        if (in.remaining() > 2) {

            while (in.remaining() >= 2) {

                if (messageLen == -1)
                    messageLen = in.getShort();

                if (in.remaining() >= messageLen) {
                    newMessage(in,out);
                    messageLen = -1;
                    returnValue = true;
                } else
                    break;
            }
        }

        return returnValue;
    }

    private void newMessage(IoBuffer in,ProtocolDecoderOutput out){
        byte[] data = new byte[messageLen-2];
        in.get(data);
        out.write(ByteBuffer.wrap(data));
    }
}
