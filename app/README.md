# Appify Core Library

```
                   _    ____  ____ ___ _______   __  _____                    
                  / \  |  _ \|  _ \_ _|  ___\ \ / / |_   _|__  __ _ _ __ ___  
                 / _ \ | |_) | |_) | || |_   \ V /    | |/ _ \/ _` | '_ ` _ \ 
                / ___ \|  __/|  __/| ||  _|   | |     | |  __/ (_| | | | | | |
               /_/   \_\_|   |_|  |___|_|     |_|     |_|\___|\__,_|_| |_| |_|
```

The Appify Core Library is a collection of modules designed to simplify common tasks in app development, such as language selection, app
introduction, and toolbar customization. By abstracting and encapsulating complex logic, the library aims to accelerate project
implementation and reduce code complexity.

- The module is implemented according to this [Figma design](https://www.figma.com/file/SNoBRRTQpT3N42XQ1E30ow/Untitled?type=design&node-id=0%3A1&mode=design&t=VaJvrDSqJwsrpzm8-1).

## Features

## LanguageSelectorView

The LanguageSelectorView module provides a simplified approach to language selection within an app. It offers the following features:

Customizable language item appearance and behavior.
Support for displaying a predefined list of languages or a custom list.
Automatic handling of language selection and updates.
Integration with the app's configuration settings.

## IntroView

The IntroView module simplifies the implementation of app introduction screens. Its key features include:

Easy creation of introduction slides using custom layouts.
Support for defining the order and content of each slide.
Automatic navigation between slides.
Customizable "Next" button appearance and behavior.
Integration with the app's navigation flow.

## XToolbar

The XToolbar module provides a streamlined approach to toolbar customization. Its main features include:

Flexible XML attributes for customizing the toolbar's appearance.
Support for setting the title, icons, and visibility of the left and right buttons.
Customization of the background color, elevation, and visibility of the title text.
Simplified registration of click listeners for the toolbar buttons.

## Installation

To integrate the Appify Core Library into your project, follow these steps:

Add the library as a dependency in your project's build.gradle file:

Add the module to your project by following these steps:

1. Add the module to the project's `build.gradle` file:

   ```
   implementation project(path: ':appify-core')
   ```

2. Sync your project with the updated dependencies.

## Documentation and Examples
**LanguageSelectorView**: _Find step-by-step instructions, code snippets, and examples for integrating and 
customizing the LanguageSelectorView module in your app._

**IntroView**: _Explore comprehensive guidance on implementing app introduction screens using the IntroView module, 
including slide creation, customization, and integration with navigation._

**XToolbar**: _Get detailed information on using the XToolbar module to customize your app's toolbar appearance and
behavior, including title, buttons, background, and click listeners._

## Contribution
Contributions to the Appify Core Library are welcome! If you have any bug reports, feature requests, or suggestions, 
please open an issue or submit a pull request on the Gitlab repository.


## Resource Code
_Please note that the resource code provided in this library is intended for internal use within the Appify team only. 
Unauthorized distribution or sharing of the resource code is strictly prohibited._