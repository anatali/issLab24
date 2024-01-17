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
with Diagram('servicecallersArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcallers', graph_attr=nodeattr):
          display=Custom('display','./qakicons/symActorWithobjSmall.png')
          caller_1=Custom('caller_1','./qakicons/symActorSmall.png')
     servicedev=Custom('servicedev','./qakicons/server.png')
     with Cluster('ctxservice', graph_attr=nodeattr):
          servicemath=Custom('servicemath(ext)','./qakicons/externalQActor.png')
     servicedev=Custom('servicedev','./qakicons/server.png')
     caller_1 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemath
     displayweb >> Edge(color='blue', style='solid', decorate='true', label='<out &nbsp; show>',  fontcolor='blue') >> servicedev
     caller_1 >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
diag
