package com.example.nach_test.cache

interface CacheMapper<Entity, Domain> {

    fun mapFromCached(type: Entity):Domain

    fun mapToCached(type:Domain): Entity

}