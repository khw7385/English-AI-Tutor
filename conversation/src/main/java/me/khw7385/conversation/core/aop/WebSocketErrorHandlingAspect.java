package me.khw7385.conversation.core.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khw7385.conversation.application.port.inbound.AudioStreamingUseCase;
import me.khw7385.conversation.core.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WebSocketErrorHandlingAspect {
    private final AudioStreamingUseCase audioStreamingUseCase;

    @Around("@annotation(me.khw7385.conversation.core.annotation.WebSocketErrorHandling) && args(session, ..)")
    public Object handleOnError(ProceedingJoinPoint point, WebSocketSession session) throws Throwable{
        try{
            return point.proceed();
        }catch (ApplicationException e){
            log.error(e.getMessage());
            audioStreamingUseCase.disconnect(session.getId());
        }
        return null;
    }
}
