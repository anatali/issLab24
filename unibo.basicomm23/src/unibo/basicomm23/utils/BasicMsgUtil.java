package unibo.basicomm23.utils;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ApplMessageType;

public class BasicMsgUtil {
private static int count = 0;

    public static IApplMessage buildDispatch(
            String actor, String msgId, String content, String dest){
        return new ApplMessage(msgId, ApplMessageType.dispatch.toString(),
                actor, dest, content, ""+count++);
    }
    public static IApplMessage buildRequest(
            String actor, String msgId, String content, String dest){
        return new ApplMessage(msgId, ApplMessageType.request.toString(),
                actor, dest, content, ""+count++);
    }
    public static IApplMessage buildReply(
            String actor, String msgId, String content, String dest){
        return new ApplMessage(msgId, ApplMessageType.reply.toString(),
                actor, dest, content, ""+count++);
    }
    public static IApplMessage buildReplyReq(
            String actor, String msgId, String content, String dest){
        return new ApplMessage(msgId, ApplMessageType.request.toString(),
                actor, dest, content, ""+count++);
    }
    public static IApplMessage buildEvent(
            String actor, String msgId, String content ){
        return new ApplMessage(msgId, ApplMessageType.event.toString(),
                actor, "none", content, ""+count++);
    }


}
