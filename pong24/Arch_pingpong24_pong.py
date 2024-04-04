### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('pingpong24_pongArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxpong', graph_attr=nodeattr):
          pong=Custom('pong','./qakicons/symActorSmall.png')
     with Cluster('ctxping', graph_attr=nodeattr):
          ping=Custom('ping(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='startgame', **evattr, decorate='true', fontcolor='darkgreen') >> pong
     sys >> Edge( label='stopgame', **evattr, decorate='true', fontcolor='darkgreen') >> pong
     pong >> Edge(color='blue', style='solid',  decorate='true', label='<ball &nbsp; >',  fontcolor='blue') >> ping
diag
