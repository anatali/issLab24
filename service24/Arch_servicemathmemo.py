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
with Diagram('servicemathmemoArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice', graph_attr=nodeattr):
          display=Custom('display','./qakicons/symActorSmall.png')
          caller_test=Custom('caller_test','./qakicons/symActorSmall.png')
          storage=Custom('storage','./qakicons/symActorWithobjSmall.png')
          servicemath=Custom('servicemath','./qakicons/symActorSmall.png')
          actionexec=Custom('actionexec','./qakicons/symActorWithobjSmall.png')
     servicedev=Custom('servicedev','./qakicons/server.png')
     actionexec >> Edge(color='magenta', style='solid', decorate='true', label='<getfibo<font color="darkgreen"> getfiboanswer</font> &nbsp; >',  fontcolor='magenta') >> storage
     caller_test >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemath
     display >> Edge(color='blue', style='solid', decorate='true', label='<out &nbsp; show>',  fontcolor='blue') >> servicedev
     actionexec >> Edge(color='blue', style='solid',  decorate='true', label='<show &nbsp; >',  fontcolor='blue') >> display
     caller_test >> Edge(color='blue', style='solid',  decorate='true', label='<out &nbsp; >',  fontcolor='blue') >> display
diag