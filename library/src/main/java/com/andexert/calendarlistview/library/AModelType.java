package com.andexert.calendarlistview.library;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lyd
 * @date 18/8/20
 * @desription
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({Config.TYPE_NORMAL, Config.TYPE_SINGLE})
public @interface AModelType {
}

