package com.group2.AutismAssesmentBackend.mappers;

public interface Mapper <A,B>{
    B mapTo(A a);
    A mapFrom(B b);
}
