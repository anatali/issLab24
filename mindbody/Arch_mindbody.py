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
with Diagram('mindbodyArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxmb', graph_attr=nodeattr):
          mind=Custom('mind','./qakicons/symActorSmall.png')
          body=Custom('body','./qakicons/symActorSmall.png')
          worldmock=Custom('worldmock','./qakicons/symActorSmall.png')
          display=Custom('display','./qakicons/symActorWithobjSmall.png')
     f=Custom('f','./qakicons/server.png')
     f >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> servicemath
     worldmock >> Edge(color='blue', style='solid',  decorate='true', label='<sound &nbsp; >',  fontcolor='blue') >> body
     body >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
     body >> Edge(color='blue', style='solid',  decorate='true', label='<sensed &nbsp; >',  fontcolor='blue') >> mind
     mind >> Edge(color='blue', style='solid',  decorate='true', label='<mindcmd &nbsp; >',  fontcolor='blue') >> body
diag
