package com.xuanwu.ai.base.exception.assertion;




import com.xuanwu.ai.base.exception.BaseException;
import com.xuanwu.ai.base.exception.ValidationException;
import com.xuanwu.ai.base.exception.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * <pre>
 *
 * </pre>
 *
 * @author gourd.hu
 * @date 2019/5/2
 */
public interface ValidationExceptionAssert extends IResponseEnum, BaseAssert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ValidationException(this, args, msg);
    }

    @Override
    default BaseException newException(String message,Object... args) {
        String msg = MessageFormat.format(message, args);
        return new ValidationException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ValidationException(this, args, msg, t);
    }

}
