package com.example.qldrl.functions;

public class CustomPasswordEncoder {
    public static String encode(String password) {
        // Simple custom hash function
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash * 31 + password.charAt(i);
        }
        return Integer.toString(hash);
    }

    public static Boolean matches(String rawPass, String encodePass){
        return encode(rawPass) == encodePass;
    }

    public static String getCustomStudentId(String sId) {
        if (sId != null && sId.length() >= 9) {
            return sId.substring(5, 7) + sId.substring(1, 3) + sId.substring(7);
        }
        return null;
    }

    public static String getCustomEvaluationId(String eva, Integer sem) {
        return eva + sem;
    }

    public static String getCustomAdvisorId(String classCode) {
        if (classCode != null && classCode.length() >= 10) {
            // Lấy mã ngành từ ký tự thứ 5 đến 7
            String majorCode = classCode.substring(5, 7);

            // Lấy năm từ ký tự thứ 2 đến 3
            String year = classCode.substring(1, 3);

            // Lấy mã lớp từ ký tự thứ 8 đến 9
            String classPart = classCode.substring(7, 9);

            // Tạo mã cố vấn bằng cách ghép nối "CV" với mã ngành, năm và mã lớp
            return "CV" + majorCode + year + classPart;
        }
        return null;
    }
}

