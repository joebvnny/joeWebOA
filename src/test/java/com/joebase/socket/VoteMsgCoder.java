package com.joebase.socket;

import java.io.IOException;

public interface VoteMsgCoder {

    byte[] toWire(VoteMsg msg) throws IOException;

    VoteMsg fromWire(byte[] input) throws IOException;
}