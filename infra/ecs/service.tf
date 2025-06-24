resource "aws_ecs_service" "ecs_service" {
  name            = "wefood-api"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.ecs_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = [aws_subnet.public1.id]
    security_groups  = [aws_security_group.fargate_sg.id]
    assign_public_ip = true
  }

  #depends_on = [aws_lb_listener.http]
}
