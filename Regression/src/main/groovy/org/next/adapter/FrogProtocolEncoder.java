package org.next.adapter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.ByteBuffer;

public class FrogProtocolEncoder implements ProtocolEncoder {
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        byte[] b = (byte[])message;

        out.write(IoBuffer.wrap(b));
    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}
