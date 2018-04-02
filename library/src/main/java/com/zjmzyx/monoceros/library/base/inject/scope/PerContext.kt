package com.zjmzyx.monoceros.library.base.inject.scope


import javax.inject.Qualifier

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PerContext(val value: String = "Application")
