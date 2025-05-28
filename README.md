```
 ░▒▓██████▓▒░░▒▓███████▓▒░       ░▒▓█▓▒░▒▓███████▓▒░ ░▒▓██████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓███████▓▒░  
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░ 
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░ 
░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░       ░▒▓█▓▒░░▒▓██████▓▒░░▒▓█▓▒▒▓███▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓██████▓▒░  
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░        
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░        
 ░▒▓██████▓▒░░▒▓███████▓▒░ ░▒▓██████▓▒░░▒▓████████▓▒░░▒▓██████▓▒░ ░▒▓██████▓▒░░▒▓█▓▒░▒▓████████▓▒░ 

```
                                                                                                                                                                                                                                                                                                                                                                                                        
# OBJ2GUI2 - Automatic Swing GUI Generator

<div align="center">

![Java](https://img.shields.io/badge/Java-8%2B-blue)
![Swing](https://img.shields.io/badge/Swing-GUI-green)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Version](https://img.shields.io/badge/Version-1.4.4-orange)

**Generate Swing GUIs automatically from annotated Java objects**

[Features](#features) • [Installation](#installation) • [Quick Start](#quick-start) • [Documentation](#documentation) • [Examples](#examples)

</div>

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Core Concepts](#core-concepts)
- [Annotations](#annotations)
- [Advanced Features](#advanced-features)
- [Examples](#examples)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

OBJ2GUI2 is a powerful Java library that automatically generates Swing GUI interfaces from annotated Java objects. It uses introspection and a rich annotation system to create dynamic, editable forms with minimal code. Perfect for creating property editors, configuration interfaces, and data entry forms.

### Why OBJ2GUI2?

- **Reduce boilerplate**: Generate complete GUIs with just annotations
- **Type-safe**: Automatic input validation based on field types
- **Multi-selection**: Edit multiple objects simultaneously
- **Extensible**: Plugin system for custom types
- **Internationalization**: Built-in label management with XML

## ✨ Features

### Core Features
- 🔄 **Automatic GUI Generation** - Create complex forms from simple annotations
- 📝 **Multiple Input Types** - TextFields, Sliders, ComboBoxes, DatePickers, and more
- 🎯 **Multi-Object Editing** - Edit multiple objects at once with difference highlighting
- 🌳 **Nested Object Support** - Recursive editing of complex object hierarchies
- 📋 **Collection Support** - Built-in editors for Lists and Arrays

### Advanced Features
- 🔌 **Plugin System** - Extend with custom type handlers
- 🌍 **I18n Support** - Automatic label management with locale support
- ✅ **Validation** - Built-in and custom validators
- 🎨 **Layout Control** - Flexible layout management with MigLayout
- 🌲 **Tree View** - Hierarchical object navigation
- 🔗 **Binding System** - Map IDs to objects for ComboBoxes

## 📦 Installation

### Maven
```xml
<dependency>
    <groupId>io.github.warnotte</groupId>
    <artifactId>obj2gui2</artifactId>
    <version>1.4.4</version>
</dependency>
```

### Gradle
```gradle
implementation 'io.github.warnotte:obj2gui2:1.4.4'
```

## 🚀 Quick Start

### 1. Create an annotated class

```java
import io.github.warnotte.obj2gui2.*;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.*;

public class Person {
    
    @PROPERTY_interface(gui_type = gui_type.TEXTFIELD)
    private String name;
    
    @PROPERTY_interface(gui_type = gui_type.SLIDER, min = 0, max = 120)
    private int age;
    
    @PROPERTY_interface(gui_type = gui_type.COMBO)
    private Gender gender;
    
    @PROPERTY_interface(gui_type = gui_type.CHECKBOX)
    private boolean active;
    
    @PROPERTY_interface(gui_type = gui_type.DATEPICKER)
    private Date birthDate;
    
    // Getters and setters...
}
```

### 2. Generate the GUI

```java
// Create objects to edit
List<Person> persons = Arrays.asList(new Person(), new Person());

// Generate the magic panel
JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(
    persons, null);

// Add to your frame
JFrame frame = new JFrame("Person Editor");
frame.add(new JScrollPane(panel));
frame.setSize(400, 300);
frame.setVisible(true);
```

## 📚 Core Concepts

### JPanelMagique

The central class that generates GUI panels from annotated objects.

```java
// Basic usage
JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(
    selection, bindings);

// With all options
JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(
    parent,          // Parent panel (for event propagation)
    selection,       // List of objects to edit
    recursive,       // Edit nested objects
    bindings,        // ID-to-object bindings
    bindingsEnum,    // Enum bindings
    onlyAnnotated,   // Only show annotated fields
    showTreeView     // Show tree view buttons
);
```

### Multi-Selection Editing

When editing multiple objects:
- **Same values**: Display normally
- **Different values**: Highlight in orange
- **Last object rule**: Display value from last selected object

```java
// Enable new system (shows last object value instead of "Different values")
JPanelMagique.newSystem = true;
```

### Event System

OBJ2GUI2 uses a custom event system to notify changes:

```java
panel.addMyEventListener(new MyEventListener() {
    @Override
    public void myEventOccurred(MyChangedEvent evt) {
        System.out.println("Object changed!");
    }
});
```

## 🏷️ Annotations

### @PROPERTY_interface

Main annotation for configuring property display:

```java
@PROPERTY_interface(
    gui_type = gui_type.TEXTFIELD,  // Component type
    readOnly = false,                // Make field read-only
    isDisplayLabel = true,           // Show label
    min = 0,                         // Minimum value (for sliders)
    max = 100,                       // Maximum value
    divider = 1.0,                   // Divider for sliders
    extension = ".txt",              // File extension (for file choosers)
    isDirectoryOnly = false,         // Directory selection only
    displayFormat = "#0.00",         // Number format
    editFormat = "#0.00"             // Edit format
)
```

### GUI Types

```java
public enum gui_type {
    TEXTFIELD,           // Simple text input
    TEXTAREA,            // Multi-line text
    TEXTPANE,            // Rich text
    CHECKBOX,            // Boolean checkbox
    COMBO,               // ComboBox
    SLIDER,              // JSlider
    FLATSLIDER,          // Custom flat slider
    DATEPICKER,          // Date selection
    COLORCHOOSER,        // Color picker
    FILECHOOSER,         // File selection
    DECIMALFORMATTEDTEXTFIELD,  // Formatted numbers
    MASKFORMATTEDTEXTFIELD,     // Masked input
    REGEXPFORMATTEDFIELD        // Regex-validated input
}
```

### @PROPERTY_FIELD_XXXABLE

Mark fields containing editable objects:

```java
public class Order {
    @PROPERTY_FIELD_XXXABLE
    private Customer customer;  // Creates nested editor
}
```

### @PROPERTY_FIELD_LISTABLE

Mark list fields for collection editing:

```java
public class Invoice {
    @PROPERTY_FIELD_LISTABLE
    private List<InvoiceItem> items;  // Creates list editor with +/- buttons
}
```

### @PROPERTY_button

Add action buttons to the panel:

```java
public class Report {
    @PROPERTY_button(
        method_name = "generateReport",
        text = "Generate",
        toolTipText = "Generate the report",
        threadedMethod = true  // Run in separate thread
    )
    private String dummyField;
    
    public void generateReport() {
        // Method called when button clicked
    }
}
```

### Layout Annotations

```java
@PROPERTY_MIGLAYOUT(
    LayoutConstraints = "fill, gap 5",
    ColumnConstraints = "[right]rel[grow,fill]",
    RowConstraints = "[]5[]",
    labelPosition = lblposition.RIGHT
)
public class MyClass {
    // Fields...
}
```

## 🔧 Advanced Features

### Binding System

Map integer IDs to objects for ComboBoxes:

```java
// Create materials list
List<Material> materials = getMaterials();

// Create binding
Binding binding = new Binding(
    materials,          // List of Identifiable objects
    Product.class,      // Target class
    "materialId"        // Field name
);

// Use in panel generation
List<Binding> bindings = Arrays.asList(binding);
JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(
    products, bindings);
```

### Plugin System

Create custom handlers for specific types:

```java
// Simple plugin
public class MyTypePlugin implements OBJ2GUIPlug<MyType, JTextField> {
    @Override
    public Class<MyType> getType() { return MyType.class; }
    
    @Override
    public JTextField build(Object value, List<?> selection, 
                           Method getter, Method setter, 
                           JPanelMagique panel) {
        JTextField field = new JTextField();
        if (value != null) {
            field.setText(value.toString());
        }
        return field;
    }
    
    @Override
    public Object getValue(Object component) {
        return new MyType(((JTextField)component).getText());
    }
    
    @Override
    public void refresh(Object value, JComponent component) {
        ((JTextField)component).setText(value.toString());
    }
}

// Register plugin
JPanelMagique.registerPlugin(new MyTypePlugin());
```

### Validation System

Add custom validators:

```java
// Create validator
Validator<Person, Integer> ageValidator = new Validator<Person, Integer>() {
    @Override
    public Integer valideValue(Person object, Integer oldValue, Integer newValue) 
            throws ValidationException {
        if (newValue < 0 || newValue > 150) {
            throw new ValidationException("Age must be between 0 and 150");
        }
        return newValue;
    }
};

// Register validator
Method setAgeMethod = Person.class.getMethod("setAge", int.class);
JPanelMagique.registerValidator(setAgeMethod, ageValidator);
```

### Label Management

Automatic label persistence with XML:

```java
// Set label directory
JPanelMagique.LABEL_XML_DIRECTORY = "config/labels";

// Labels are automatically saved/loaded
// Files: config/labels/obj2gui_Labels/en/ClassName_labels.xml

// Manual label management
GUI2XMLLabel.SaveLabel(panel, "labels.xml");
GUI2XMLLabel.LoadLabel(panel, "labels.xml");
```

### Tree View

Navigate complex object hierarchies:

```java
JTreeMagique tree = JTreeMagique.GenerateJTreeFromSelectionAndBindings(
    selection, bindings, bindingsEnum, 
    "labels.xml",     // Label file
    true,             // Use tabbed pane
    true              // Show tree view buttons
);
```

## 📝 Examples

### Complete Example: Task Manager

```java
@PROPERTY_MIGLAYOUT(
    LayoutConstraints = "fill, gap 5",
    ColumnConstraints = "[right]rel[grow,fill]"
)
public class Task {
    
    @PROPERTY_interface(gui_type = gui_type.TEXTFIELD)
    private String title;
    
    @PROPERTY_interface(gui_type = gui_type.TEXTAREA)
    private String description;
    
    @PROPERTY_interface(gui_type = gui_type.COMBO)
    private Priority priority;
    
    @PROPERTY_interface(gui_type = gui_type.DATEPICKER)
    private Date dueDate;
    
    @PROPERTY_interface(gui_type = gui_type.SLIDER, min = 0, max = 100)
    private int progress;
    
    @PROPERTY_interface(gui_type = gui_type.CHECKBOX)
    private boolean completed;
    
    @PROPERTY_FIELD_XXXABLE
    private TaskDetails details;
    
    @PROPERTY_FIELD_LISTABLE
    private List<SubTask> subTasks;
    
    @PROPERTY_button(
        method_name = "save",
        text = "Save Task",
        toolTipText = "Save changes to database"
    )
    private String saveButton;
    
    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    // Methods
    public void save() {
        // Save to database
    }
    
    // Getters and setters...
}
```

### Array Editing

```java
public class DataSet {
    @PROPERTY_interface
    private double[] values;  // Automatically creates table editor
    
    @PROPERTY_interface
    private String[][] matrix;  // 2D table editor
}
```

### Custom Formatting

```java
public class Financial {
    @PROPERTY_interface(
        gui_type = gui_type.DECIMALFORMATTEDTEXTFIELD,
        displayFormat = "$#,##0.00",
        editFormat = "0.00"
    )
    private BigDecimal amount;
    
    @PROPERTY_interface(
        gui_type = gui_type.MASKFORMATTEDTEXTFIELD,
        displayFormat = "###-##-####"  // SSN format
    )
    private String ssn;
}
```

## 🗂️ JPanel_MultiPropsEditor — Universal Inspector Panel

`JPanel_MultiPropsEditor` is a *ready-to-use* property inspector that can display and edit a **heterogeneous selection** of domain objects inside a single, scrollable Swing panel.  
Think of it as the “Inspector” you find in tools like Unity, Blender or IntelliJ designer — generated for you, in one line of code.

### Feature highlights

- **Drop-in inspector** for *mixed* selections (different runtime classes).
- **Graceful empty state** – shows a “Nothing selected” card until items are provided.
- **Automatic grouping** – partitions the current selection by class and stacks one `JPanelMagique` per group.
- **Bidirectional event relay** – implements `MyEventListener` and forwards every `MyChangedEvent` to its own listeners, so you only have to register once.
- **Programmatic refresh** – call `refresh()` to redraw all embedded panels after model changes.
- **Highly tunable constructor** – toggle recursion, “only-annotated” mode, custom `Binding` / `BindingEnum` lists and tree-view buttons directly in the constructor.
- **Localisable UI strings** – edit `JPanel_MultiPropsEditor.text_nothing_selected` to translate the placeholder.

### How it works

| What happens | Internal mechanism |
|--------------|--------------------|
| *Selection is empty* | A `CardLayout` switches to a placeholder card called **NONE**. |
| *Mixed classes in selection* | `setTaskProperties` groups by `Class<?>` (`Collectors.groupingBy`) and creates one `JPanelMagique` per bucket. |
| *Each sub-panel stretches horizontally* | After creation, each panel’s max width is set to `Integer.MAX_VALUE` while preserving its preferred height. |
| *Scrollable inspector* | All sub-panels live inside a `JScrollPane` with smoother scroll (`unitIncrement = 16`). |
| *Change propagation* | Every sub-panel registers the inspector as a `MyEventListener`; `myEventOccurred` relays to external listeners. |
| *Runtime updates* | `refresh()` loops through child `JPanelMagique` instances and calls their own `refresh()` method. |

### Quick start

    // 1. Build a mixed selection
    List<Object> selection = List.of(new Sphere(), new Cube(), new PointLight());

    // 2. Create the inspector (recursive editing enabled)
    JPanel_MultiPropsEditor inspector = new JPanel_MultiPropsEditor(true);
    inspector.setTaskProperties(selection);

    // 3. Listen to change events just once
    inspector.addMyEventListener(evt -> System.out.println("Something changed!"));

    // 4. Plug into your UI
    frame.add(inspector, BorderLayout.EAST);

### Advanced usage

    List<Binding> binds = List.of(
        new Binding(materials, Product.class, "materialId"));
    List<BindingEnum> bindsEnum = List.of(/* … */);

    JPanel_MultiPropsEditor inspector = new JPanel_MultiPropsEditor(
            /* isRecursive           */ true,
            /* binds                 */ binds,
            /* bindsEnum             */ bindsEnum,
            /* onlyAnnotatedMethods  */ false,
            /* showTreeViewButtons   */ true);

    inspector.setTaskProperties(selection);

### Localisation tip

    // Change the placeholder text globally
    JPanel_MultiPropsEditor.text_nothing_selected = "No objects selected";


## 📖 API Reference

### Main Classes

| Class | Description |
|-------|-------------|
| `JPanelMagique` | Main panel generator |
| `JTreeMagique` | Tree view for object navigation |
| `Binding` | ID-to-object binding |
| `BindingEnum` | Enum binding |
| `GUI2XMLLabel` | Label management |

### Utility Methods

```java
// Register custom title for class
JPanelMagique.registerTitleBorder(Person.class, "Person Details");

// Register title for list fields
JPanelMagique.registerTitleBorderForList(
    Order.class, "items", "Order Items");

// Configure buttons
JPanelMagique.setTEXT_BOUTON_ADD_ELEMENT("Add");
JPanelMagique.setIMG_BOUTON_ADD_ELEMENT(addIcon);
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

### Development Setup

1. Clone the repository
2. Import as Maven project
3. Run tests: `mvn test`
4. Build: `mvn package`

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👏 Credits

Created by **Warnotte Renaud** (2011-2024)

---

<div align="center">
Made with ❤️ for the Java Swing community
</div>