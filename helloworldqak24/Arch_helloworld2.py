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
with Diagram('helloworld2Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxhello', graph_attr=nodeattr):
          worker=Custom('worker','./qakicons/symActorSmall.png')
          display=Custom('display(coded)','./qakicons/codedQActor.png')
     f=Custom('f','./qakicons/server.png')
     f >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> servicemath
     worker >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
diag
