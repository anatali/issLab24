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
with Diagram('mqttdemoArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxmmqtt', graph_attr=nodeattr):
          publisher=Custom('publisher','./qakicons/symActorSmall.png')
          receiver=Custom('receiver','./qakicons/symActorSmall.png')
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> publisher
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> receiver
diag
