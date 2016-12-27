######################################################################
# Automatically generated by qmake (3.0) Tue Dec 27 08:38:29 2016
######################################################################

TEMPLATE = app
TARGET = AAAA-Engine
CONFIG -= qt

INCLUDEPATH += . AAAA-Engine/

DEFINES += PC_GLES SDL2_PORT NO_SDL_MIXER

LIBS += -lSDL2 # -lSDL2_mixer
LIBS += -lGLESv1_CM -lEGL

# Input
HEADERS += AAAA-Engine/bullets.h \
           AAAA-Engine/camera.h \
           AAAA-Engine/drawmob.h \
           AAAA-Engine/game.h \
           AAAA-Engine/gamemenu.h \
           AAAA-Engine/gamescene.h \
           AAAA-Engine/intro.h \
           AAAA-Engine/mobai.h \
           AAAA-Engine/mobs.h \
           AAAA-Engine/narrator.h \
           AAAA-Engine/particles.h \
           AAAA-Engine/playcontrol.h \
           AAAA-Engine/render.h \
           AAAA-Engine/sprites.h \
           AAAA-Engine/vars.h \
           AAAA-Engine/veryblend.h \
           AAAA-Engine/wlight.h \
           AAAA-Engine/wmapgen.h \
           AAAA-Engine/wmapload.h \
           AAAA-Engine/zcore.h \
           AAAA-Engine/zcsound.h \
           AAAA-Engine/zctables.h \
           AAAA-Engine/zeditmode.h \
           AAAA-Engine/zgui.h \
           AAAA-Engine/zlext.h \
           AAAA-Engine/zlmath.h \
           AAAA-Engine/zresm.h \
           AAAA-Engine/ztypes.h

SOURCES += AAAA-Engine/bullets.c \
           AAAA-Engine/camera.c \
           AAAA-Engine/drawmob.c \
           AAAA-Engine/game.c \
           AAAA-Engine/gamemenu.c \
           AAAA-Engine/gamescene.c \
           AAAA-Engine/intro.c \
           AAAA-Engine/main.c \
           AAAA-Engine/mobai.c \
           AAAA-Engine/mobs.c \
           AAAA-Engine/narrator.c \
           AAAA-Engine/particles.c \
           AAAA-Engine/playcontrol.c \
           AAAA-Engine/render.c \
           AAAA-Engine/sprites.c \
           AAAA-Engine/vars.c \
           AAAA-Engine/veryblend.c \
           AAAA-Engine/wlight.c \
           AAAA-Engine/wmapgen.c \
           AAAA-Engine/wmapload.c \
           AAAA-Engine/zcore.c \
           AAAA-Engine/zcsound.c \
           AAAA-Engine/zctables.c \
           AAAA-Engine/zeditmode.c \
           AAAA-Engine/zgui.c \
           AAAA-Engine/zlext.c \
           AAAA-Engine/zlmath.c \
           AAAA-Engine/zresm.c
