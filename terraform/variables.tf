variable "region" {
  description = "Define the deploy region"
  default     = "us-east-1"
}

variable "name" {
  description = "Name of the application"
  default     = "DuckMail-server-01"
}

variable "env" {
  description = "Envioment of the application"
  default     = "prod"
}

variable "ami" {
  description = "AWS AMI to be used"
  default     = "ami-0dba2cb6798deb6d8"
}

variable "instance_type" {
  description = "Virtual Machine hardware configuration"
  default     = "t2.micro"
}

variable "repo" {
  description = "Repository of the application"
  default     = "https://github.com/KauanMO/DuckMail"
}