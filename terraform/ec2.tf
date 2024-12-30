resource "aws_instance" "server" {
  ami           = var.ami
  instance_type = var.instance_type
  vpc_security_group_ids = [aws_security_group.security_group.id]
  user_data = file("user_data.sh")

  tags = {
    Name        = var.name
    Enviroment  = var.env
    Provisioner = "Terraform"
    Repo        = var.repo
  }
}

resource "aws_security_group" "security_group" {
  name        = "securitygroup"
  description = "Grants HTTP and Internet access"

  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 65535
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}