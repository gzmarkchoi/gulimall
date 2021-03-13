package com.mci.common.constant;

public class WarehouseConstant {
    public enum PurchaseStatusEnum {
        CREATED(0, "created"), ASSIGNED(1, "assigned"),
        RECEIVE(2, "receive"), FINISH(3, "finish"),
        ERROR(4, "error");

        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "created"), ASSIGNED(1, "assigned"),
        BUYING(2, "receive"), FINISH(3, "finish"),
        FAIL(4, "fail");

        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
