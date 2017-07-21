package util

import java.time.LocalDateTime
import javax.inject.Singleton

import conf.util._
import domain.util._
import domain.users._
import domain.storage._
import domain.content._
import domain.location._
import domain.demographics._
import domain.organisation._
import domain.security.Roles
import domain.syslog.SystemLogEvents
import services.security.AuthenticationService


@Singleton
object factories {
  /*****************************************************
    *                    content                       *
    ****************************************************/
  def getCategory: Category = {
    Category(id = "1", name = "HIV PREVENTION", description = "how to prevent HIV")
  }

  def getSource: Source = {
    Source(org="CPUT", id="1",name= ContentKeys.RAW, description = "raw data")
  }

  def getContentType: ContentType = {
    ContentType(id = "1", name = "TEXT", description = "text content")
  }

  def getRawContent: RawContent = {
    RawContent(org = "CPUT", id = "1", dateCreated = LocalDateTime.now(), creator = "test@test.com",
    source = "1", category = "1", title = "scala code", content = "this is scala",
    contentTypeId = "1", status = ContentKeys.RAW, state = HashcodeKeys.ACTIVE)
  }

  def getEditedContent: EditedContent = {
    EditedContent(org = "CPUT", id = "1", dateCreated = LocalDateTime.now(), creator = "test@test.com",
    source = "1", category = "1", title = "scala code", content = "this is scala",
    contentTypeId = "1", status = ContentKeys.EDITED, state = HashcodeKeys.ACTIVE
    )
  }

  def getPublishedContent: PublishedContent = {
    PublishedContent(org = "CPUT", id = "1", dateCreated = LocalDateTime.now(), creator = "test@test.com",
    source = "1", category = "1", title = "scala code", content = "this is scala",
    contentTypeId = "1", status = ContentKeys.PUBLISHED, state = HashcodeKeys.ACTIVE
    )
  }

  def getMedia: Media = {
    Media(contentId = "1", id = "1", description = "uml diagram",url = "https://ww.me.com/uml.jpg",
    mime = ".jpg",date = LocalDateTime.now(),state = HashcodeKeys.ACTIVE)
  }


  /*****************************************************
    *                   demographics
    ****************************************************/

  def getGender: Gender = {
    Gender(id = "1",name = GenderKeys.MALE ,state = HashcodeKeys.ACTIVE)
  }

  def getLanguageProficiency: LanguageProficiency = {
    LanguageProficiency(id = "1", name = "Isizulu", state = HashcodeKeys.ACTIVE)
  }

  def getLanguage: Language = {
    Language(id = "1", name = "Isizulu", state = HashcodeKeys.ACTIVE)
  }

  def getMaritalStatus: MaritalStatus = {
    MaritalStatus(id = "1", name = MaritalStatusList.MARRIED, state = HashcodeKeys.ACTIVE)
  }

  def getRace: Race = {
    Race(id = "1", name = "African", state = HashcodeKeys.ACTIVE)
  }

  def getDemoRole: Role = {
    Role(id = "1", name = RolesID.READER, description = "readonly user", state = HashcodeKeys.ACTIVE)
  }

  /*****************************************************
    *                   Location
    ****************************************************/

  def getAddressType: AddressType = {
    AddressType(id = "1", name = "HOME", state = HashcodeKeys.ACTIVE)
  }

  def getContactType: ContactType = {
    ContactType(id = "1", name = "mobile", state = HashcodeKeys.ACTIVE)
  }

  def getLocationType: LocationType = {
    LocationType(id = "1", name = "Location type", code = "3281", state = HashcodeKeys.ACTIVE)
  }

  /*****************************************************
    *                   organisation
    ****************************************************/

  def getLocation: Location = {
    Location(id = "1",org = "cput",name = "cape town", locationTypeId="53",code="7580", latitude="68",
      longitude="454",parentId="1",state = HashcodeKeys.ACTIVE,date = LocalDateTime.now())
  }

  def getOrganisationLogo: OrganisationLogo = {
    OrganisationLogo(org = "cput",id="3",url="http://www.google.com/e.jpg",size = Some("512MB"),
      description = "cput logo",mime = ".jpg",date = LocalDateTime.now())
  }

  def getOrganisation: Organisation = {
    Organisation(id = "1",name = "cput",details = Map(),adminAttached = "admin",
      date = LocalDateTime.now(),state = HashcodeKeys.ACTIVE)
  }

  /*****************************************************
    *           storage and  system log event
    ****************************************************/


  def getFileResults: FileResults = {
    FileResults(id = "1", url = "http://www.google.com/file.png", size = Some("512MB"), mime = ".png")
  }

  def getStorageUrl: StorageUrl = {
    StorageUrl(id = "1", name = "org logo", url = "http://cput.ac.za/logo.png")
  }

  def getSystemLog: SystemLogEvents = {
    SystemLogEvents(siteId = "CPUT", id = "1", eventName = "email failed",eventType = "creating new user",
    message = "user already exist",date = LocalDateTime.now())
  }

  /*****************************************************
    *                   users
    ****************************************************/

  def getUserAddress: UserAddress = {
    UserAddress(emailId="test@test.com", id="1", addressTypeId="1", description="16 abbey st camelot",
      postalCode="7530",date= LocalDateTime.now(),state = HashcodeKeys.ACTIVE)
  }

  def getUserContact: UserContact = {
    UserContact(emailId = "test@test.com",id= "1", contactTypeId = "1",
      contactNumber = "+2774 791 3185", date = LocalDateTime.now(), state = HashcodeKeys.ACTIVE)
  }

  def getUserDemographics: UserDemographics = {
    UserDemographics(emailId="test@test.com",id="1",genderId = "1",raceId = "1", dateOfBirth = LocalDateTime.now(),
      maritalStatusId = MaritalStatusList.MARRIED,numberOfDependencies = 5,date =LocalDateTime.now(), state = HashcodeKeys.ACTIVE)
  }

  def getUserImages: UserImages= {
    UserImages(org = "CPUT", emailId = "test@test.com", id="2", description = "profile pic",
      url="http://www.cput.ac.za/logo.png", mime = ".png", size = Some("512MB") ,date = LocalDateTime.now())
  }

  def getUserLanguage: UserLanguage = {
    UserLanguage(emailId = "test@test.com", id = "1", languageId = "1", reading = "eng", writing = "eng",
      speaking = "eng", date = LocalDateTime.now(),state = HashcodeKeys.ACTIVE)
  }

  def getUserRole: UserRole = {
    UserRole(siteId= "CPUT", emailId="test@test.com",date = LocalDateTime.now(), roleId = RolesID.READER)
  }

  def getUser: User = {
    User(siteId="CPUT",email="test@test.com",firstName=Some("First Name"),lastName=Some("Last Name"),
      middleName= None, screenName="CODER",password= AuthenticationService.apply.getHashedPassword("passwd"),
      state = HashcodeKeys.ACTIVE,date=LocalDateTime.now() )
  }

  /*****************************************************
    *                  utils
    ****************************************************/
  def getItemStatus: ItemStatus = {
    ItemStatus(itemId = "1",date = LocalDateTime.now(), status = HashcodeKeys.ACTIVE, description = "USER")
  }

  def getKeys: Keys = {
    Keys(id = "1", value = HashcodeKeys.CDN_URL, status = HashcodeKeys.ACTIVE)
  }

  def getMail: Mail = {
    Mail(siteId = "gmail",id = "1",key = HashcodeKeys.MAILKEY,value ="mail.host.com",
      host = "smtp.google.com",port = "587",state = HashcodeKeys.ACTIVE, date = LocalDateTime.now())
  }

  def getRoles: Roles = {
    Roles(id = RolesID.READER, rolename = RolesID.READER)
  }
}
