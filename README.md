# OBJ2GUI2
                                                                    
    ,----..                                                                                    
   /   /   \                            ,----,                                        ,----,   
  /   .     :   ,---,                 .'   .' \                          ,--,       .'   .' \  
 .   /   ;.  \,---.'|         .--.  ,----,'    |                  ,--, ,--.'|     ,----,'    | 
.   ;   /  ` ;|   | :       .--,`|  |    :  .  ;,----._,.       ,'_ /| |  |,      |    :  .  ; 
;   |  ; \ ; |:   : :       |  |.   ;    |.'  //   /  ' /  .--. |  | : `--'_      ;    |.'  /  
|   :  | ; | ':     |,-.    '--`_   `----'/  ;|   :     |,'_ /| :  . | ,' ,'|     `----'/  ;   
.   |  ' ' ' :|   : '  |    ,--,'|    /  ;  / |   | .\  .|  ' | |  . . '  | |       /  ;  /    
'   ;  \; /  ||   |  / :    |  | '   ;  /  /-,.   ; ';  ||  | ' |  | | |  | :      ;  /  /-,   
 \   \  ',  / '   : |: |    :  | |  /  /  /.`|'   .   . |:  | : ;  ; | '  : |__   /  /  /.`|   
  ;   :    /  |   | '/ :  __|  : './__;      : `---`-'| |'  :  `--'   \|  | '.'|./__;      :   
   \   \ .'   |   :    |.'__/\_: ||   :    .'  .'__/\_: |:  ,      .-./;  :    ;|   :    .'    
    `---`     /    \  / |   :    :;   | .'     |   :    : `--`----'    |  ,   / ;   | .'       
              `-'----'   \   \  / `---'         \   \  /                ---`-'  `---'          
                          `--`-'                 `--`-'                                        

## About

This library aimed to generated swing interface base on introspection and annotation of DTOs.

It's based on the experience i made called OBJ2GUI [see](https://code.google.com/archive/p/obj2gui/) or [here](https://web.archive.org/web/20111127223810/http://code.google.com/p/obj2gui/)

Generation is based on variables annotations with multiple parameters, helping to design more sophisticated GUI.

Works as simple as it needs only some lines of code to generate a "JPanel" reflecting an objects where variables becomes viewable/editable.

All input controls are normally done automatically to forbid bad input of the user avoiding developer headache. (eg : Textfield binded to an Integer : user shouldn't be allowed to put non digit characters).

{{< alert >}}
The difference with OBJ2GUI is that OBJ2GUI2 can support collection of Object instead of just on Object.
{{< /alert >}}

## Maven repository
[Find all version here](https://mvnrepository.com/artifact/io.github.warnotte/obj2gui2)

## Import Maven
```
<!-- https://mvnrepository.com/artifact/io.github.warnotte/obj2gui2 -->
<dependency>
    <groupId>io.github.warnotte</groupId>
    <artifactId>obj2gui2</artifactId>
    <version>1.3.0</version>
</dependency>
```

## Import Graddle

```
// https://mvnrepository.com/artifact/io.github.warnotte/obj2gui2
implementation 'io.github.warnotte:obj2gui2:1.3.0'
```
