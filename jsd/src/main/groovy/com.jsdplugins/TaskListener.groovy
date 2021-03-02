package com.jsdplugins

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * 监控每一个Task执行开始与结束，以及工程build情况
 */
public class TaskListener implements TaskExecutionListener, BuildListener{

    private static final String TAG = "[JSD]"

    private long duration

    private TaskListener(){}

    @Override
    void buildStarted(Gradle gradle) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"build started!")
    }

    @Override
    void settingsEvaluated(Settings settings) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"project evaluated!")
    }

    @Override
    void projectsLoaded(Gradle gradle) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"project loaded!")
    }

    @Override
    void projectsEvaluated(Gradle gradle) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"project evaluated!")
    }

    /**
     * 完成之后会调用
     * @param buildResult
     */
    @Override
    void buildFinished(BuildResult buildResult) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"build finished!")
    }

    @Override
    void beforeExecute(Task task) {
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"task before : "+task.name)
        duration = System.currentTimeMillis()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        duration = System.currentTimeMillis() - duration
        println(new Date().format("yyyy-MM-dd HH:mm:ss")+TAG+"task after : ${task.name} duration:$duration")
        if(task.name == "packageRelease"){
            //do selft
            println("------>jsd----->self")
        }
    }
}