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
with Diagram('servicemathArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice', graph_attr=nodeattr):
          caller1=Custom('caller1','./qakicons/symActorSmall.png')
          caller2=Custom('caller2','./qakicons/symActorSmall.png')
          display=Custom('display','./qakicons/symActorSmall.png')
          servicemath=Custom('servicemath','./qakicons/symActorSmall.png')
          actionexecutor=Custom('actionexecutor','./qakicons/symActorSmall.png')
     caller2 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemath
     caller1 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemath
     servicemath >> Edge(color='blue', style='solid',  decorate='true', label='<show &nbsp; >',  fontcolor='blue') >> display
     actionexecutor >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
     caller1 >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
     caller2 >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
diag
