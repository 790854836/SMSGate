package com.zx.sms.handler.sgip;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

import org.marre.sms.SmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zx.sms.codec.cmpp.wap.AbstractLongMessageHandler;
import com.zx.sms.codec.sgip12.msg.SgipDeliverRequestMessage;
import com.zx.sms.codec.sgip12.msg.SgipDeliverResponseMessage;
@Sharable
public class SgipDeliverLongMessageHandler extends AbstractLongMessageHandler<SgipDeliverRequestMessage> {
	private final Logger logger = LoggerFactory.getLogger(SgipDeliverLongMessageHandler.class);
	@Override
	protected void response(ChannelHandlerContext ctx, SgipDeliverRequestMessage msg) {
		
		//短信片断未接收完全，直接给网关回复resp，等待其它片断
		SgipDeliverResponseMessage responseMessage = new SgipDeliverResponseMessage(msg.getHeader());
		responseMessage.setResult((short)0);
		ctx.writeAndFlush(responseMessage);
	}

	@Override
	protected boolean needHandleLongMessage(SgipDeliverRequestMessage msg) {
	
		return true;
	}

	@Override
	protected String generateFrameKey(SgipDeliverRequestMessage msg) {
		return msg.getUsernumber();
	}

	@Override
	protected void resetMessageContent(SgipDeliverRequestMessage t, SmsMessage content) {
		t.setMsgContent(content);
	}

}
