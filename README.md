# SpringData - Postgres - Testcontainers - Liquibase - Example

Use Prostgres Junit Testcontainer an initializes with Liquibase

Datasource properties will be set in AnimalRepositoryIT ContextConfiguration where AnimalRepositoryIT.Initializer is used.
The Initializer uses the property values from Postgres TestContainer