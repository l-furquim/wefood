variable "region" {
  type = string
}

variable "vpc_cidr" {
  type = string
}

variable "public_subnet_cidrs" {
  type = list(string)
}

variable "sg_cidr" {
  type = list(string)
}

variable "env" {
  type = string  
}