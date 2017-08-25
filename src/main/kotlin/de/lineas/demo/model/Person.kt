package de.lineas.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Person(@Id @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE) var id: Long = 0,
                  var first_name: String = "",
                  var last_name: String = "",
                  var company_name: String = "",
                  var address: String = "",
                  var city: String = "",
                  var county: String = "",
                  var state: String = "",
                  var zip: String = "",
                  var phone1: String = "",
                  var phone2: String = "",
                  var email: String = "",
                  var web: String = "")
