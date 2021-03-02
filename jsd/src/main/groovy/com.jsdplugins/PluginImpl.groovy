package com.jsdplugins

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginImpl implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+"-----------------jsd start---------")
        println("--the jsd gradle--")
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+"-----------------jsd end-----------")
        def android = project.extensions.findByType(AppExtension);
        android.registerTransform(new InsertTransform())
        project.gradle.addListener(new TaskListener())
    }
}