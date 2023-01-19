# Java 3D Game Programming 

## Study Goal 
```
3D Game을 Java로 만들어보는 것을 목표로 한다. 
3D에 대한 기본적인 개념을 알아보도록 하자. 

```

## Study Log 
```
3D Game Programming - Episode 1 - Window
    java swing을 사용하기에 꽤나 익숙하게 시작할 수 있다. 
3D Game Programming - Episode 2 - Game Loop 
    thread를 응용한다. 별로 어려운 내용은 없다. 
3D Game Programming - Episode 3 - Arrays
    pixel value를 Array안에 저장하겠다는 내용이다. 
3D Game Programming - Episode 4 - Drawing Pixels!
    랜덤한 값의 pixel Array를 화면에 쏴준다. 
3D Game Programming - Episode 4.5 - How Rendering Works
    렌더링과 관련된 간략한 설명이다. 
3D Game Programming - Episode 5 - Playing with Pixels!
    sin과 cos의 수학적인 기법을 사용하여 화면에 다양한 연출을 보여준다. 
    -> 참고로 출력문은 절대 연산과정에 삽입하지 마라. 속도가 지나치게 느려짐. 
3D Game Programming - Episode 6 - Performance Boosting
    리팩토링하는 과정이다. 렌더링하지 않아도될 픽셀은 그냥 continue해도 된다. 
    그 외의 테크닉이 소개된다. 이건 다른 분야에서도 유용하게 써먹을 수 있는 내용이다. 
3D Game Programming - Episode 7 - FPS Counter
    FPS Counter를 만드는 공식이 어떻게 되는지 이해가 가지 않을 수 있다.
    특정 시간에 한해 렌더링이 일어나는 수를 측정하고 그 두 가지 수 사이의 계산이 진행된다. 
3D Game Programming - Episode 8 - Alpha Suppport and More
    픽셀 연산에 관여되는 수를 바꿈으로써 다양한 연출이 가능하다. 
3D Game Programming - Episode 9 - Beginning 3D 
    3D에 대한 기본적인 내용을 다룬다. 정면에 보이는 간단한 바닥과 천장을 보여준다. 
    해당 공식이 이해가 안될텐데 관련된 설명 중 가장 근접한 설명은 아래 설명인 것 같다. 
    /*
    Comment below:
    From what I can tell, he basically derived the rendering mechanism from the following perspective equations,
    sx = x / z tan(fov / 2)
    sy = y / z tan(fov / 2)
    Where sx, sy are normalized screen coords and x, y, z are 3d space coords
    You can exclude the tan to get an fov of 90 degrees

    Then since already know sx, sy and y(the floor/ceiling distance/height) you loop through the y coordinate of the screen and for each row solve for z:
    z = 1 / sy * y
    Then you have a z distance for every horizontal row of pixels
    Next, for every horizontal row you loop through all the pixels based on the distance of the row from the camera,
    x = sx * z

    Now you have top down 2d coords x and z which you can directly translate to texture coordinates
    Am i on the right track? I'm very tired
     */
3D Game Programming - Episode 10 - Floors and Animation
3D Game Programming - Episode 11 - Rotation 
3D Game Programming - Episode 12 - User Input 
3D Game Programming - Episode 13 - Render Distance Limiter!
3D Game Programming - Episode 14 - Basic Mouse Movement 
3D Game Programming - Episode 15 - Textures + More! 
3D Game Programming - Episode 16 - Walking, Crouching, Sprinting + More
3D Game Programming - Episode 16.5 - Exporting Runnable Jars
3D Game Programming - Episode 17 - Small Adjustments + Birthday!
3D Game Programming - Episode 17.5 - Creating an Applet
3D Game Programming - Episode 18 - The Beginning of Walls
3D Game Programming - Episode 18.1 - A Few More Things
3D Game Programming - Episode 18.5 - Creating an EXE File in Java
3D Game Programming - Episode 19 - Rendering Walls
3D Game Programming - Episode 20 - Continuing Walls, Fixing Bugs, and Managing Crashes
3D Game Programming - Episode 21 - Texturing Walls, Fixing Clipping, and Fixing the Mouse
3D Game Programming - Episode 22 - Random Level Generator + Properly Fixing Clipping
3D Game Programming - Episode 23 - Graphical User Interface(GUI) Launcher
3D Game Programming - Episode 24 - Making Our Launcher Work
3D Game Programming - Episode 25 - Writing and Reading Files
3D Game Programming - Episode 26 - Custom Resolutions
3D Game Programming - Episode 27 - Decorating the Launcher
3D Game Programming - Episode 28 - Continuing our Custom Launcher!
3D Game Programming - Episode 29 - Launching The Game
3D Game Programming - Episode 30 - Colour Processing In-Depth
3D Game Programming - Episode 31 - Sprites!
3D Game Programming - Episode 32 - Sprite Mapping
3D Game Programming - Episode 33 - High Resolution Rendering
3D Game Programming - Episode 34 - Entities
```

