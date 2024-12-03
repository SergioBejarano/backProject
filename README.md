# SISTEMA DE GESTIÓN BIBLIOTECARIA

El presente documento detalla las funcionalidades y especificaciones técnicas del Sistema de
Gestión Bibliotecaria que se implementa del Colegio Nuestra Señora de la Sabiduría. Este
sistema se diseña para facilitar la gestión de libros, estudiantes, responsables económicos, y
el registro de préstamos y devoluciones de los libros de la biblioteca del colegio.


## MÓDULO DE GESTIÓN DE ESTUDIANTES Y RESPONSABLES ECONÓMICOS

Este módulo permite la gestión de toda la información de los estudiantes y sus respectivos
responsables económicos. Cada estudiante está vinculado a un responsable económico, quien
será notificado en caso de que un préstamo venza. Cada responsable ecónomico puede
representar a varios estudiantes siempre y cuando estos tengan un parentezco.

#### Datos de los Estudiantes:

- Código del estudiante: Identificación única del estudiante.
- Nombre completo.
- Curso: Ej. Pre-Jardín, Jardín, Primero, etc.
- Año académico: Ej. 2024.
- Datos de los Responsables Económicos:
- Nombre completo.
- Documento de identificación: Cédula o equivalente.
- Relación con el estudiante: Padre, tutor, etc.
- Correo electrónico: Será utilizado para las notificaciones.
- Teléfono de contacto.
- Dirección.
- Cada estudiante puede estar asociado a un solo responsable económico, y este será notificado sobre el estado de los préstamos del estudiante


# MODELO DE ARQUITECTURA

![Arquitectura Microservicios](https://github.com/user-attachments/assets/fc2d3a0f-76d9-448d-84cd-f659bd49184b)



# PERSISTENCIA

Se diseña la base de datos `usermanagement` con los siguientes detalles:


![image](https://github.com/user-attachments/assets/74e6a899-d20c-4a92-af16-d1a8dbf52f21)




Posteriormente, el equipo de desarrollo toma la decisión de crear la base de datos tipo Relacional con `Postgres`. 
Esta se despliaga en Azure. 

![image](https://github.com/user-attachments/assets/b896c484-75c9-4492-9485-dbf0f382979a)

Tenienedo en cuenta las siguientes especificaciones:

- Creación de las tablas

```sh
CREATE TABLE public.users (
    id VARCHAR PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE public.responsibles (
    document VARCHAR PRIMARY KEY,
    site_document VARCHAR,
    name VARCHAR,
    phone_number VARCHAR,
    email VARCHAR
);

CREATE TABLE public.grades (
    name VARCHAR PRIMARY KEY
);

CREATE TABLE public.courses (
    name VARCHAR PRIMARY KEY,
    grade_name VARCHAR
);

CREATE TABLE public.students (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL,
    document VARCHAR NOT NULL,
    document_type VARCHAR,
    course_name VARCHAR,
    responsible_document VARCHAR
);

CREATE TABLE public.administrators (
    id VARCHAR PRIMARY KEY,
    email VARCHAR NOT NULL,
    name VARCHAR NOT NULL


);
```

- Restricciones

```sh
ALTER TABLE public.courses
ADD CONSTRAINT fk_grade FOREIGN KEY (grade_name)
REFERENCES public.grades(name) ON DELETE SET NULL;

ALTER TABLE public.students
ADD CONSTRAINT fk_user FOREIGN KEY (id)
REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE public.administrators
ADD CONSTRAINT fk_administrator_user FOREIGN KEY (id)
REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE public.students
ADD CONSTRAINT fk_course FOREIGN KEY (course_name)
REFERENCES public.courses(name) ON DELETE SET NULL;

ALTER TABLE public.students
ADD CONSTRAINT fk_responsible FOREIGN KEY (responsible_document)
REFERENCES public.responsibles(document) ON DELETE SET NULL;
```

- Usuario de conexión

```sh
CREATE USER librarydirector WITH PASSWORD 'userManagement2024';
```

- Permisos para el usuario

```sh
GRANT INSERT, UPDATE, DELETE ON TABLE responsibles TO librarydirector;
GRANT INSERT, UPDATE, DELETE ON TABLE administrators TO librarydirector;
GRANT INSERT, UPDATE, DELETE ON TABLE students TO librarydirector;
```

## GIT FLOW

![image](https://github.com/user-attachments/assets/3d7357e6-4f33-4de2-a604-328218f742f4)


## SONARLINT
Es una herramienta estática de análisis de código diseñada para integrarse directamente en los entornos de desarrollo como IntelliJ IDEA, Eclipse, Visual Studio Code, entre otros. Su propósito principal es identificar y solucionar problemas relacionados con la calidad del código, como:

* Errores de codificación.
* Malas prácticas.
* Vulnerabilidades de seguridad.
* Problemas de mantenibilidad.
* Código duplicado.

Desde IntelliJ se realiza la instación a través de la ruta:
`File > Settings > Plugins > SonarLint` 

![image](https://github.com/user-attachments/assets/ef5020a2-4186-4968-9239-dc63500cd4c2)

## SONARQUBE

Es una herramienta poderosa de análisis estático de código que ayuda a identificar problemas relacionados con la calidad, seguridad y mantenibilidad del software. Proporciona métricas detalladas, como cobertura de pruebas, deuda técnica y vulnerabilidades, permitiendo a los desarrolladores mejorar continuamente su código.

Se siguen las siguientes especificaciones:
- Se descarga la imagen de docker con el siguiente comando ```docker pull sonarqube``` 
- Se arranca el servicio de SonarQube con el siguiente comando ```docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest```
- Se valida funcionamiento ```docker ps -a```
- Se inicia sesión en sonar ```localhost:9000``` cambiando la clave por defecto ( usuario y contraseña admin ). 

![image](https://github.com/user-attachments/assets/be1076c5-ce21-4a2c-b444-eb1ec853508c)

Quality Gate: Passed

Esto indica que el proyecto cumple con los criterios establecidos en el Quality Gate. Aunque existen algunos problemas, no son lo suficientemente graves como para impedir que el proyecto sea considerado de calidad aceptable.


## COBERTURA - JACOCO

![image](https://github.com/user-attachments/assets/e37daa07-abd5-471c-8c5b-a7b4e999d5e4)

