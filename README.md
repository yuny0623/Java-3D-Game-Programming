# Java 3D Game Programming 

## TO FROM 
```
2023.01.19 ~ 
```

## Why
```
학부 강좌에 그래픽스 강좌가 없어서 직접 간략한 게임을 만들어보고 대략적인 개념을 잡아보도록 한다. 
```

## Study Goal 
```
3D Game을 Java로 만들어보는 것을 목표로 한다. 
3D Programming에 대한 기본적인 개념을 알아보도록 하자. 
그래픽스에 대한 대략적인 이해를 할 수 있도록 하자. 
```

## How
```
직접 관련 강의를 구현하고 커스텀하도록 한다. 
매 주차별 강의에 대한 정리와 후기를 남길 수 있도록 한다. 
```

## Study Log 
```
3D Game Programming - Episode 1 - Window
    Java Swing을 사용하기에 꽤나 익숙하게 시작할 수 있다. 
    (이전에 Swing을 써봐서 꽤 익숙한 내용이다.)
    
3D Game Programming - Episode 2 - Game Loop 
    게임 루프를 만들기 위해 thread를 응용한다. 별로 어려운 내용은 없다.
    그냥 thread start하는 내용들이 있다.  
    
3D Game Programming - Episode 3 - Arrays
    pixel value를 Array안에 저장하겠다는 내용이다. 
    
3D Game Programming - Episode 4 - Drawing Pixels!
    랜덤한 값의 pixel Array를 화면에 쏴준다. 이 부분에서 이전에 본 적 없는 API가 사용된다. 
    아래의 코드 조각들이 등장하는데 모르고 지나치기엔 꽤 중요한 부분이다. 
    픽셀 연산과는 상관없지만 직접 화면에 쏘는 바로 그 과정이 이 부분이다. 
    /*
    BufferedImage img 
    pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData()

    BufferStrategy
    createBufferStrategy(3)

    Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    */
    
3D Game Programming - Episode 4.5 - How Rendering Works
    렌더링과 관련된 간략한 설명이다. 
    
3D Game Programming - Episode 5 - Playing with Pixels!
    sin과 cos의 수학적인 기법을 사용하여 화면에 다양한 연출을 보여준다. 
    -> 참고로 출력문은 절대 연산과정에 삽입하지 말자. 속도가 지나치게 느려진다. 
    
3D Game Programming - Episode 6 - Performance Boosting
    리팩토링하는 과정이다. 렌더링하지 않아도될 픽셀은 그냥 continue해도 된다. 
    그 외의 테크닉이 소개된다. 이건 다른 분야에서도 유용하게 써먹을 수 있는 내용이다.
    for문을 더 적게 돌 수 있는 방법이 소개된다.  
    
3D Game Programming - Episode 7 - FPS Counter
    FPS Counter를 만드는 공식이 어떻게 되는지 이해가 가지 않을 수 있다.
    특정 시간에 한해 렌더링이 일어나는 수를 측정하고 그 두 가지 수 사이의 계산이 진행된다. 
    아마 프레임 율에 관한 이해가 있어야지 이해되는 부분이라고 생각된다. 
    
3D Game Programming - Episode 8 - Alpha Suppport and More
    픽셀 연산에 관여되는 수를 바꿈으로써 다양한 연출이 가능하다. 
    
3D Game Programming - Episode 9 - Beginning 3D 
    3D에 대한 가장 기본적인 구현이 나온다. 정면에 보이는 간단한 바닥과 천장을 보여준다. 
    나오는 공식이 이해가 안될텐데 관련된 설명 중 가장 근접한 설명은 아래 설명인 것 같다. 
    사실 공식을 봐도 모르겠고 설명도 잘 이해가 안간다. 아마 실제 3D 좌표계와 화면 상의 
    차이를 공식으로 연결짓는 내용인데 난이도가 꽤 있다. 
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
    floor 생성에 관한 내용이다.  
    
3D Game Programming - Episode 11 - Rotation 
    floor 상에서 대각선, 전진, 후진, 우회전, 좌회전 등을 가능하게함. 이때 rotation
    의 경우 회전 동작인데 회전을 하려면 원을 그릴 수 있어야함. 원을 그리기 위해서 sin과 cos을 활용한다. 
    
3D Game Programming - Episode 12 - User Input
    우리가 게임을 조작하는 것처럼 W A S D키를 사용해서 앞,뒤,좌,우 움직임을 실행할 수 있도록 한다. 
    Key Event를 받아서 해당 키에 대한 조작을 실행한다. 
     
3D Game Programming - Episode 13 - Render Distance Limiter!
    floor에서 먼 거리까지 렌더링을 하기 때문에 보기 좋지 않은 부분이 있다. 
    이걸 gradient까지 넣어서 자연스럽게 거리표현까지 하면서도 먼 거리를 표현할 수 있을까? 
    그에 대해 다룬다. 

3D Game Programming - Episode 14 - Basic Mouse Movement
    마우스 커서를 추가한다. 마우스 커서가 밖으로 나가면 더 이상 회전하지 않는 이슈가 있다. 
    해당 이슈는 이후 강좌에서 추가적으로 수정될 예정임. 마우스의 현재 위치를 알아낸 뒤 해당 위치가 
    다음 위치와 다른지, 더 큰지, 작은지를 비교해서 좌우로 회전할 방향을 정한다. 히전은 이전에 Episode 11의 rotation에서
    다룬 기능을 그대로 사용한다.  
    
3D Game Programming - Episode 15 - Textures + More! 
    텍스쳐 적용을 구현한다. 텍스쳐 이미지의 int array데이터를 얻어와서 그걸 pixels인 array에
    넣어주면 된다. 

    이슈: 
    텍스쳐를 적용하기 위해서는 8비트x8비트 짜리 원하는 이미지를 생성하고 그 이미지의 
    경로를 지정해줘서 이미지를 읽어와야하는데 이때 사용하는 Texture.class.getResource(fileName)에 이슈가 있다. 
    java.lang.IllegalArgumentException: input == null! 이라는 Exception이 발생하는데 이건 
    경로를 제대로 지정해줘도 문제가 발생한다. 그래서 이 기능 대신에 FileInputStream을 사용해서 경로를 넘기면
    제대로된 데이터를 얻을 수 있다. 아래 링크를 참조하면 된다.
    
    해결방법: 
    https://stackoverflow.com/questions/15424834/java-lang-illegalargumentexception-input-null-when-using-imageio-read-to-lo

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

## Study Review 
```


```