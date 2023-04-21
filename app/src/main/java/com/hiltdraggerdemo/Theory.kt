package com.hiltdraggerdemo

class Theory {
    /*
    Step 1:
    @HiltAndroidApp
    class ExampleApplication : Application() { ... }

    Step 2: in android Manifest file
    .name = "ExampleApplication"

   Step 3:Inject dependencies into Android classes
   @AndroidEntryPoint
   class ExampleActivity : AppCompatActivity() { ... }

Hilt currently supports the following Android classes:
Application (by using @HiltAndroidApp)
ViewModel (by using @HiltViewModel)
Activity
Fragment
View
Service
BroadcastReceiver

Step 4:Define Hilt bindings using @Inject
class Employee @Inject constructor() { .....}

Step 5:To obtain dependencies from a component, use the @Inject annotation to perform field injection
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject lateinit var emp: Employee
  ...
}
     */
}