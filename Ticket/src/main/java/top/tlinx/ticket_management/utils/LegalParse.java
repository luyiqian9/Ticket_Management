package top.tlinx.ticket_management.utils;

import top.tlinx.ticket_management.exception.GlobalException;

public class LegalParse {
    public static int isLegal(String str) throws GlobalException {
        int tid = -1;
        try {
            tid = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new GlobalException(500, "列车号不合法");
        }

        return tid;
    }
}
