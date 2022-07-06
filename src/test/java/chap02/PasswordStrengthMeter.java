package chap02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        if(s==null || s.isEmpty())
            return PasswordStrength.INVALID;
        int metCount = getMetCriteriaCount(s);

        if(metCount<=1) return PasswordStrength.WEAK;
        if(metCount==2) return PasswordStrength.NORMAL;

        return PasswordStrength.STRONG;
    }

    private int getMetCriteriaCount(String s) {
        int metCount=0;

        if(s.length() >= 8) metCount++ ;
        if(meetsContainingNumberCriteria(s)) metCount++;
        if(meetsContainingUpperCaseCriteria(s)) metCount++;
        return metCount;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        boolean hasNumber=false;
        for (char ch : s.toCharArray()) {
            if(ch>='0' && ch<='9'){
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingUpperCaseCriteria(String s) {
        boolean hasUpperCase=false;
        for (char ch : s.toCharArray()) {
            if(Character.isUpperCase(ch))
                return true;
        }
        return false;
    }
}
