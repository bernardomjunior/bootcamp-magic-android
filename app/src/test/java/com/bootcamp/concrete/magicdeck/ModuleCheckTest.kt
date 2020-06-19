package com.bootcamp.concrete.magicdeck

import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.di.repositoryModule
import com.bootcamp.concrete.magicdeck.di.viewModelModule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest(){

    @Test
    fun checkModules() = checkModules{
        modules(repositoryModule, viewModelModule, networkModule)
    }

}