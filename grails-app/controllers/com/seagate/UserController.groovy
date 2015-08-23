package com.seagate

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

  FileUploadService fileUploadService
  static final okcontents = ['image/png', 'image/jpeg', 'image/gif']
  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  def index(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    respond User.list(params), model:[userCount: User.count()]
  }

  def show(User user) {
    respond user
  }

  def create() {
    respond new User(params)
  }

  @Transactional
  def save(User user) {
    if (user == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    if (user.hasErrors()) {
      transactionStatus.setRollbackOnly()
      respond user.errors, view:'create'
      return
    }

    def avatarImage = request.getFile('avatar')
    if(!avatarImage.isEmpty()){
      println "Class: ${avatarImage.class}"
      println "Name: ${avatarImage.name}"
      println "OriginalFileName: ${avatarImage.originalFilename}"
      println "Size: ${avatarImage.size}"
      println "ContentType: ${avatarImage.contentType}"

      // List of OK mime-types
      if (!okcontents.contains(avatarImage.getContentType())) {
        flash.message = "Avatar must be one of: ${okcontents}"
        render(view:'create', model:[user:user])
        return
      }

      user.avatarPath = fileUploadService.uploadFile(avatarImage, "${user.name}.png")
    }


    user.save flush:true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
        redirect user
      }
      '*' { respond user, [status: CREATED] }
    }
  }

  def edit(User user) {
    respond user
  }

  @Transactional
  def update(User user) {
    if (user == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    if (user.hasErrors()) {
      transactionStatus.setRollbackOnly()
      respond user.errors, view:'edit'
      return
    }

    user.save flush:true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
        redirect user
      }
      '*'{ respond user, [status: OK] }
    }
  }

  @Transactional
  def delete(User user) {

    if (user == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    user.delete flush:true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
        redirect action:"index", method:"GET"
      }
      '*'{ render status: NO_CONTENT }
    }
  }

  protected void notFound() {
    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
        redirect action: "index", method: "GET"
      }
      '*'{ render status: NOT_FOUND }
    }
  }
}
