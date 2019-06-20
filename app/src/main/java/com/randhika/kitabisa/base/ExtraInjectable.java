package com.randhika.kitabisa.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

public interface ExtraInjectable {

    /**
     * Override this method to inject the values from extras bundle
     * This method is automatically called in onCreate of Activity or Fragment
     */
    void injectExtras(@NonNull Bundle extras);
}