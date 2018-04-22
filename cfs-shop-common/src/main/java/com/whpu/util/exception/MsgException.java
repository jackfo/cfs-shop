package com.whpu.util.exception;

import com.whpu.util.json.CodeMsg;

public class MsgException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public MsgException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
