package com.seagate

import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile
import grails.web.context.ServletContextHolder

@Transactional
class FileUploadService {

  def String uploadFile(MultipartFile file, String name) {
    def servletContext = ServletContextHolder.getServletContext()
    // /assets/grails_logo.png
    def root = servletContext.getRealPath("/")
    def imgDir = "${root}avatar"
    println "PATH {imgDir}"

    def pathDir = new File(imgDir)

    if(!file.isEmpty()) {
      file.transferTo(new File("${imgDir}/${name}"))
      println "SAVE FILE: ${imgDir}/${name}"
      return "${imgDir}/${name}"
      } else {
        println "FILE ${file.inspect()} was empty!"
        return null
      }

    }
  }
