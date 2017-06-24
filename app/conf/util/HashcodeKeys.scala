package conf.util

import javax.inject.Singleton

@Singleton
object HashcodeKeys {
  def GeocodingAPI = "GeocodingAPI"

  def CDN_URL: String = "CDN_URL"

  def MAILORG: String = "HASHCODE"

  def DEALLOCATED: String = "DEALLOCATED"

  def ALLOCATED: String = "ALLOCATED"

  def DISABLED: String = "DIASBLED"

  def ACTIVE: String = "ACTIVE"

  def INACTIVE: String = "INACTIVE"

  def MARGINTOKENKEY: String = "MARGINTOKENKEY"

  def RSSFEED = "RSS"

  def PUBLICKEY = "PUBLICKEY"

  def ISSUER = "HASHCODE.ZM"

  def SITEUSERS = "SITEUSERS"

  def SITEACCESS = "Site Access"

  def EMAIL = "email"

  def ROLE = "role"

  def ORGCODE = "orgCode"

  def AGENT = "agent"

  def MAILKEY = "MAILKEY"

}
