package com.example.nach_test.data.movies

interface DataMapper <Entity, Domain> {

    fun mapFromEntity(type: Entity): Domain

    fun mapToEntity(type: Domain): Entity
}