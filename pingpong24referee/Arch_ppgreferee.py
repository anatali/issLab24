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
with Diagram('ppgrefereeArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxreferee', graph_attr=nodeattr):
          referee=Custom('referee','./qakicons/symActorSmall.png')
     with Cluster('ctxping', graph_attr=nodeattr):
          ping=Custom('ping(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxpong', graph_attr=nodeattr):
          pong=Custom('pong(ext)','./qakicons/externalQActor.png')
     referee >> Edge( label='startgame', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     pong >> Edge(color='blue', style='solid',  decorate='true', label='<ballview &nbsp; >',  fontcolor='blue') >> referee
     ping >> Edge(color='blue', style='solid',  decorate='true', label='<ballview &nbsp; >',  fontcolor='blue') >> referee
diag
