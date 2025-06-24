resource "aws_ecs_task_definition" "ecs_task" {
  family                   = "my-fargate-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "1024"
  memory                   = "2048"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name      = "wefood-discovery",
      image     = "furqas/wefood-discovery:latest",
      essential = true,
      portMappings = [
        {
          containerPort = 8084,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-gateway",
      image     = "furqas/wefood-gateway:latest",
      portMappings = [
        {
          containerPort = 8086,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-payment",
      image     = "furqas/wefood-payment:latest",
      portMappings = [
        {
          containerPort = 8081,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-mailsender",
      image     = "furqas/wefood-mailsender:latest",
      portMappings = [
        {
          containerPort = 8085,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-profile",
      image     = "furqas/wefood-profile:latest",
      portMappings = [
        {
          containerPort = 8083,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-notification",
      image     = "furqas/wefood-notification:latest",
      portMappings = [
        {
          containerPort = 8082,
          protocol      = "tcp"
        }
      ],
    },
    {
      name      = "wefood-pedido",
      image     = "furqas/wefood-pedido:latest",
      portMappings = [
        {
          containerPort = 8080,
          protocol      = "tcp"
        }
      ],
    }
  ])
}
