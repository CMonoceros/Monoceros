package com.zjmzyx.monoceros.library.base.inject.scope

import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment
