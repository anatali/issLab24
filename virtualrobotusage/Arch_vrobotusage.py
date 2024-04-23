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
with Diagram('vrobotusageArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxvrobotusage', graph_attr=nodeattr):
          vrclienthl=Custom('vrclienthl','./qakicons/symActorSmall.png')
          basicrobot0=Custom('basicrobot0','./qakicons/symActorWithobjSmall.png')
          vrobserver=Custom('vrobserver','./qakicons/symActorSmall.png')
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> vrobserver
     sys >> Edge( label='obstacle', **evattr, decorate='true', fontcolor='darkgreen') >> vrobserver
     vrclienthl >> Edge(color='magenta', style='solid', decorate='true', label='<step<font color="darkgreen"> stepdone stepfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot0
diag
