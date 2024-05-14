package de.fhws.fiw.fds.manager.server.api.models;

public enum SemesterType {
    SPRING(1),
    AUTUMN(2);

    public final int semester;

    SemesterType(int semester) {
        this.semester = semester;
    }
}
