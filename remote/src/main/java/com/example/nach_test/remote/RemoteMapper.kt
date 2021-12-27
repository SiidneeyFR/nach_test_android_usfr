package com.example.nach_test.remote

interface RemoteMapper<in Remote, out Domian> {
    fun mapFromRemote(remote: Remote): Domian
}