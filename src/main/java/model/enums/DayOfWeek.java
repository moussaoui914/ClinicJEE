package model.enums;

public enum DayOfWeek {
    MONDAY("Lundi", java.time.DayOfWeek.MONDAY),
    TUESDAY("Mardi", java.time.DayOfWeek.TUESDAY),
    WEDNESDAY("Mercredi", java.time.DayOfWeek.WEDNESDAY),
    THURSDAY("Jeudi", java.time.DayOfWeek.THURSDAY),
    FRIDAY("Vendredi", java.time.DayOfWeek.FRIDAY),
    SATURDAY("Samedi", java.time.DayOfWeek.SATURDAY),
    SUNDAY("Dimanche", java.time.DayOfWeek.SUNDAY);

    private final String label;
    private final java.time.DayOfWeek javaDay;

    DayOfWeek(String label, java.time.DayOfWeek javaDay) {
        this.label = label;
        this.javaDay = javaDay;
    }

    public String getLabel() { return label; }
    public java.time.DayOfWeek getJavaDay() { return javaDay; }

    public static DayOfWeek fromJava(java.time.DayOfWeek jd) {
        for (DayOfWeek d : values()) {
            if (d.javaDay == jd) return d;
        }
        throw new IllegalArgumentException("Jour non supporte : " + jd);
    }
}