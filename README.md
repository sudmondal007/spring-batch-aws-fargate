This is created for dockerized container of a Spring BATCH application that can be used to create a Fargate JOB definition.

provide "jobRoleArn" and "executionRoleArn" to same IAM role for BATCH


executionRoleArn --> The task execution role grants the Amazon ECS agents permission to make AWS API calls on your behalf. Execution role is required when using repository credentials for your container(s).

jobRoleArn --> Identity and Access Management (IAM) role is required when execution command is enabled. An IAM role provides the container in your job with permissions to use the AWS APIs. This feature uses Amazon Elastic Container Service (Amazon ECS) IAM roles for tasks functionality.
