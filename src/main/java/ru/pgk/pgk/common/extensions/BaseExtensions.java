package ru.pgk.pgk.common.extensions;

import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;

import java.util.function.Supplier;

public class BaseExtensions {

    public static Boolean exist(Supplier<?> exceptionSupplier) {
        try {
            exceptionSupplier.get();
            return true;
        }catch (ResourceNotFoundException ignore) {
            return false;
        }
    }
}
